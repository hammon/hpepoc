import org.testng.annotations.Test;

import java.io.File;


public class ChangeDetector {

    @Test
    public void test(){
        String[] files = getChangedFiles();
        for(int i = 0;i < files.length;i++){
            System.out.println("file: " + files[i]);
            getChangedLines(new File(System.getProperty("user.dir") + "/" + files[i]));
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

    public void getChangedLines(File file){

        System.out.println("getChangedLines: " + file.getAbsolutePath().replace(System.getProperty("user.dir") + "/",""));

        ProcessUtils cmd = new ProcessUtils();
        String res = cmd.exec("git diff -U0 master..testContent " + file.getAbsolutePath().replace(System.getProperty("user.dir") + "/",""),new File(System.getProperty("user.dir")));
//        System.out.println(res);

        String[] arr = res.split("\n");
        for(int i = 0;i < arr.length;i++){
            if(arr[i].startsWith("@@")){
                System.out.println("lines: " + arr[i]);
            }
        }
    }
}
