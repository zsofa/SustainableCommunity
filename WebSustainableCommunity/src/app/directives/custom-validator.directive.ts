import { Directive, Input } from '@angular/core';
import { Validator, AbstractControl, NG_VALIDATORS, ValidationErrors, ValidatorFn } from '@angular/forms';

@Directive({
    selector: '[customValidator]',
    providers: [{ provide: NG_VALIDATORS, useExisting: CustomValidatorDirective, multi: true }]
})
export class CustomValidatorDirective implements Validator {
    @Input("customValidator") public customValidator: ValidatorFn | ValidatorFn[];

    validate(control: AbstractControl): ValidationErrors | null {
        if (this.customValidator instanceof Function)
            return this.customValidator(control);

        let errorObj: ValidationErrors = null;

        for (let validatorMethod of this.customValidator) {
            if (errorObj === null)
                errorObj = {};
            errorObj = Object.assign(errorObj, validatorMethod(control));
        }

        return errorObj;
    }
}