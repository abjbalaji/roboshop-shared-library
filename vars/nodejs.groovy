def lintChecks(){
    sh '''
        // ~/node_modules/jslint/bin/jslint.js server.js
       echo Link checks for ${COMPONENT}
       '''
        }

def call(){
    pipeline{
        agent any

        stages{
            stage('Lint Checks') {
                steps{
                    script{
                       lintChecks()
                    }

                }
            }
        }
    }


}
