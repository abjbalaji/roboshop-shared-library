def lintChecks(){
    sh '''
      # ~/node_modules/jslint/bin/jslint.js server.js
       echo Link checks for ${COMPONENT}
       '''
        }


def  sonarChecks(){
    sh '''
        sonar-scanner -Dsonar.host.url=http://172.31.9.128:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.passwd=${SONAR_PSW}
       '''
}

def call(){
    pipeline{
        agent any

        environmentp {
        SONAR = credentials('SONAR')
        }

        stages{
//            stage('Lint Checks') {
//                steps{
//                    script{
//                       lintChecks()
//                    }
//
//                }
//            }

            stage('Sonar Checks') {
                steps{
                    script{
                        sonarChecks()
                    }

                }
            }
        }
    }


}
