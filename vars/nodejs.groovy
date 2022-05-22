def lintChecks(){
    sh '''
      # ~/node_modules/jslint/bin/jslint.js server.js
       echo Link checks for ${COMPONENT}
       '''
        }




def call(){
    pipeline{
        agent any

        environment {
        SONAR = credentials('SONAR')
        }

        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()
                    }

                }
            }

            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS = "-Dsonar.sources=."
                        common.sonarChecks()
                    }

                }
            }
            stage('Test Cases') {
                parallel {
                    stage('Unit Testing') {
                        steps {
                            sh 'echo Unit Test cases'
                              }
                        }

                    stage('Integration Testing') {
                        steps {
                            sh 'echo Integration Test cases'
                              }
                        }

                    stage('Functional Testing') {
                        steps {
                            sh 'echo Functional Test cases'
                              }
                        }

                } //line for parallel




            } //line for parallel stage

            stage('Preparing Artifact')
                    {
                        when{
                            expression{env.TAG_NAME != null}
                        }
                        sh '''
                            npm install
                            zip -r ${COMPONENT}.zip node_modules server.js
                       
                           '''

                    }
            stage('Preparing Artifact')
                    {
                        when{
                            expression{env.TAG_NAME != null}
                        }
                        sh '''
                            curl -v -u  ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}.zip  http://172.31.9.189:8081/repository/${COMPONENT}/${COMPONENT}.zip
                            '''

                    }
            }
        } // line for agent
    } // line for pipeline

 //line for call