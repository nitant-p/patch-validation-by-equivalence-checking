# Patch Validator for Intelligent Tutoring System (ITS)

## Overview
This project is a component of an Intelligent Tutoring System (ITS) designed to check the semantic equivalence between a student's patched submission and a reference solution. The project is implemented in Java and is primarily used in coding assignments where semantic equivalence is crucial to evaluate student submissions.

Semantic equivalence is defined as two programs producing the same return value and output when given the same input. Our validator works for both pure functions (where return values determine equivalence) and impure functions (where outputs must match despite possible differences in control flow).

## Features
- Semantic Equivalence Verification: Compares patched and reference programs to determine whether they produce identical outputs and return values.
- Support for Impure Functions: Handles functions with side effects, ensuring equivalence based on outputs instead of just return values.
- Integration with ITS Modules: Uses ITS components such as the parser and structural aligner for program pre-processing before validation.
- Z3 Solver Integration: Translates programs into Z3 logical formulas for precise semantic analysis.
- Support for Basic Input/Output Operations: Implements basic scanf and printf functions for equivalence checking with user inputs.

## How It Works
1. Input Parsing and Structural Alignment: The patched and reference programs are parsed by the ITS parser and aligned using the ITS structural aligner.
2. Z3 Translation: Both programs are translated into Z3 logical formulas.
3. Z3 Solver: The Z3 solver processes the formulas to determine whether the two programs are semantically equivalent.
4. Output Comparison: The program evaluates the return values and output from both programs and determines equivalence.

## Documentation
### Class Diagram
![class-diagram](images/class_diagram.png)
### Sequence Diagram
![sequence-diagram](images/sequence_diagram.png)
