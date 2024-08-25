# its-patchvalidation-template

## Overview
It is very important to provide formal guarantee that our ITS generated patches are correct. This project provides a template for the patch validation module.
You need to implement the `PatchValidator` class, which is responsible for verifying the correctness of the generated patches. The `PatchValidator` class should take two programs as input and return a boolean value indicating whether the fixed program is semantically equivalent to the reference program.


## Entry Points

* This project does not have any local dependencies. Please use the parser API provided by our its-service to retrive the intermediate CFG representation of the programs. You can also find an example usage in [its-integration-services](./its-integration-services/src/test/java/sg/edu/nus/se/its/parser/ParserServiceImplTest.java).

* [sg.edu.nus.se.its.validation.PatchValidator](./its-patch-validation/src/main/java/sg/edu/nus/se/its/validation/PatchValidator.java)
```
/**
 * Verification module based on program equivalence checking.
 */
public class PatchValidator{

  public boolean patchValidation(Program referenceProgram, Program fixedProgram) {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }

}
```

* You can use `mvn clean compile test` to build and test your implementation.

## Restrictions
* You are not allowed to change any code in the [sg.edu.nus.its.its-core](./its-core), unless you get approval from tutors.
* You need to stick to the provided interfaces.
* You are not allowed to change the file/class name or move [sg.edu.nus.se.its.validation.OptimizationRepair](./its-repair-optimization/src/main/java/sg/edu/nus/se/its/repair/OptimizationRepair.java ).
* If you would require any other dependencies or libraries, you first need to seek approval by the tutors.
* You are not allowed to change any file within [.github](./.github).

## Documentation
### Class Diagram
![class-diagram](images/class_diagram.png)
### Sequence Diagram
![sequence-diagram](images/sequence_diagram.png)
