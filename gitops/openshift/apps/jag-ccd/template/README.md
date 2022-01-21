## Templates to create openshift components related to jag-ccd api deployment

### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod/tools
   ``oc process -f jag-ccd.yaml --param-file=jag-ccd.env | oc apply -f -``
