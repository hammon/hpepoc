# Governance POC
============

The purpose of this project is to demonstrate the ability to improve test automation reliability,
by employing Sonarqube code coverage capabilities.


Installing Sonarqube
--------------------

Download Sonarqube from the following link:
https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-5.5.zip

Unzip the SonarQube distribution (let's say in "C:\sonarqube" or "/etc/sonarqube")

On Windows, execute:
C:\sonarqube\bin\windows-x86-xx\StartSonar.bat

On other operating system, execute:
/etc/sonarqube/bin/[OS]/sonar.sh console

Navigate to <http://127.0.0.1:9000>

You will see SonarQube main dahboard
![SonarQube cosole](sq-empty.png)

Clone and Run the project
------------------------------

Clone the repo ```git clone git@github.com:hammon/hpepoc.git```

Execute ```gradle clean test sonarRunner``` from the project home directory

Refresh SonarQube console at <http://127.0.0.1:9000>

You will see hpepoc project in the dashboard:
![SonarQube projects](sq-projects.png)

Execute ```git diff  master..testContent src/main/java/TestServlet.java```

You will see output similar to this:

```
diff --git a/src/main/java/TestServlet.java b/src/main/java/TestServlet.java
index 1dc45e0..121daf1 100644
--- a/src/main/java/TestServlet.java
+++ b/src/main/java/TestServlet.java
@@ -50,7 +50,7 @@ public class TestServlet extends HttpServlet {
             out.print(request.getParameter("content"));
         }
         else{
-            out.print("Hello!");
+            out.print("Hello test content!");
         }
     }
```

Execute ```git checkout testContent```

The testContent branch contains a minor change that causes one of the tests to fail.

Execute ```gradle clean test sonarRunner``` again.

