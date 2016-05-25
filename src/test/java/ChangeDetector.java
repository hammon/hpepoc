import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ChangeDetector {

    @Test
    public void test(){
        String[] files = getChangedFiles();
        for(int i = 0;i < files.length;i++){
            System.out.println("file: " + files[i]);
            if(files[i].startsWith("src/main/java/")){
                Integer[] changedLines = getChangedLines(new File(System.getProperty("user.dir") + "/" + files[i]));
                for(int j = 0;j < changedLines.length;j++){
                    System.out.println("Changed line: " + changedLines[j]);
                    getTestsByLineNumber(files[i],changedLines[j]);
                }
            }
        }
    }

    public String[] getChangedFiles(){
        ProcessUtils cmd = new ProcessUtils();

        System.out.println("work dir: " + System.getProperty("user.dir"));

        String res = cmd.exec("git diff --name-only master..testContent",new File(System.getProperty("user.dir")));

        String[] arr = res.split("\n");

        //System.out.print(res);

        return arr;
    }

    public Integer[] getChangedLines(File file){

        System.out.println("getChangedLines: " + file.getAbsolutePath().replace(System.getProperty("user.dir") + "/",""));

        ProcessUtils cmd = new ProcessUtils();
        String res = cmd.exec("git diff -U0 master..testContent " + file.getAbsolutePath().replace(System.getProperty("user.dir") + "/",""),new File(System.getProperty("user.dir")));
//        System.out.println(res);

        List<Integer> changedLines = new ArrayList<Integer>();

        String[] lines = res.split("\n");
        for(int i = 0;i < lines.length;i++){
            if(lines[i].startsWith("@@")){
                System.out.println("lines: " + lines[i]);
                String[] arr = lines[i].split(" ");

 //               String lineA = arr[1];
                String lineB = arr[2];

                //added lines
                if(lineB.contains(",")){
                    String[] arrLinesMark = lineB.split(",");
                    Integer startLine = Integer.parseInt(arrLinesMark[0].replace("+",""));
                    Integer countLines = Integer.parseInt(arrLinesMark[1]);
                    for(int n = 0;n < countLines;n++){
                        changedLines.add(startLine + n);
                    }
                }
                else{
                    changedLines.add(Integer.parseInt(lineB.replace("+","")));
                }


            }
        }
        return changedLines.toArray(new Integer[changedLines.size()]);
    }

    public void getTestsByLineNumber(String fileName,Integer lineNum){
        HttpUtils http = new HttpUtils();
        String url = "http://127.0.0.1:9000/api/tests/list?sourceFileKey=michael:hpepoc:testContent:"+
                fileName + "&sourceFileLineNumber=" + lineNum;

        String res = http.get(url);

        System.out.println(res);

        printTests(res);
    }

    void printTests(String str){
        JSONObject obj = new JSONObject(str);

        if(obj.has("tests")){
            JSONArray arrTests = obj.getJSONArray("tests");
            for(int i = 0;i < arrTests.length();i++){
                JSONObject objTest = arrTests.optJSONObject(i);
                System.out.println( objTest.toString(2));
            }
        }
    }
}
