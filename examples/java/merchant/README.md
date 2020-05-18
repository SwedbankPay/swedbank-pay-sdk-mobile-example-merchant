Running a local development instance
------------------------------------
Prerequisites: Java 8 JDK or higher
```
[me@laptop merchant]$ ./gradlew bootRun
```


Zero-downtime blue-green deployment to AWS environment
------------------------------------------------------
Prerequisites:
 - Local AWS CLI setup and appropriately permissioned AWS user account
 - Set your merchant-specific details in src/main/resources/application.yml

Build your deployment package:
```
[me@laptop merchant]$ ./gradlew clean bootJar
```

Upload it to S3, use your specific bucket identifier:
```
[me@laptop merchant]$ aws s3 cp build/libs/merchant-1.0.1-SNAPSHOT.jar s3://merchant-fixture-jarbucket-1234567890123
```

Instantiate a parallel service environment serving your new deployment package, use parameters from existing fixture:
```
[me@laptop merchant]$ aws cloudformation create-stack \
    --stack-name merchant-app-BLUE \    # or GREEN, but opposite of existing environment
    --template-body file://infra/asc-app.yaml \
    --parameters ParameterKey=ElbId,ParameterValue=merchant-LoadBala-123456789012 \
                 ParameterKey=S3InstanceProfileId,ParameterValue=merchant-fixture-S3ReadingInstanceProfile-123456789012 \
                 ParameterKey=JarName,ParameterValue=merchant-1.0.1-SNAPSHOT.jar \
                 ParameterKey=JarBucketId,ParameterValue=merchant-fixture-jarbucket-1234567890123 \
                 ParameterKey=ElbSecGroupId,ParameterValue=sg-12345678901234567
```

Wait for the new environment to come into operation:
```
[me@laptop merchant]$ aws cloudformation list-stacks --stack-status-filter CREATE_COMPLETE
```

Delete obsolete parallel service environment:
```
[me@laptop merchant]$ aws cloudformation delete-stack \
    --stack-name merchant-app-GREEN     # or BLUE, but opposite of new environment
```


Set up initial service fixture, to support deployment described above:
----------------------------------------------------------------------
Prerequisites:
 - Local AWS CLI setup and appropriately permissioned AWS user account
 - Supporting Route53 DNS and certificate setup reflected in infra/elb-fixture.yaml
 - VPC reference reflected in infra/elb-fixture.yaml 
 
```
[me@laptop merchant]$ aws cloudformation  create-stack \
    --stack-name merchant-fixture \
    --template-body file://infra/elb-fixture.yaml \
    --capabilities CAPABILITY_IAM
```