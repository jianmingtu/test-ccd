## Templates to create openshift components related to jag-ccd-1-1 api deployment

### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod
   ``oc process -f jag-ccd-1-1.yaml --param-file=jag-ccd-1-1.env | oc apply -f -``
