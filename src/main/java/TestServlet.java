import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class TestServlet extends HttpServlet {

    final static Logger log = LoggerFactory.getLogger(TestServlet.class);
    //File root = new File("/home/michael/");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        response.setCharacterEncoding("UTF-8");

        if(request.getParameterMap().containsKey("responseCode")){
            responseCode(request,response);
        }
        else{
            hello(request,response);
        }
    }

    void hello(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error("Failed to get response Writer",e);
        }

        if(request.getParameterMap().containsKey("content")){
            out.print(request.getParameter("content"));
        }
        else{
            out.print("Hello!");
        }
    }

    void responseCode(HttpServletRequest request, HttpServletResponse response){

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error("Failed to get response Writer",e);
        }

        String sCode = request.getParameter("responseCode");

        try{
            int code = Integer.parseInt(sCode);
            response.setStatus(code);
            out.print(code);
        }catch (Exception e){
            response.setStatus(500);
            out.print("Failed to parse responseCode" + e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }


}
