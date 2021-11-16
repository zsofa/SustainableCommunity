import { Directive, Input } from '@angular/core';
import { AbstractControl, AsyncValidator, ValidationErrors, NG_ASYNC_VALIDATORS, AsyncValidatorFn } from '@angular/forms';
import { Observable, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';

@Directive({
    selector: '[customAsyncValidator]',
    providers: [{ provide: NG_ASYNC_VALIDATORS, useExisting: CustomAsyncValidatorDirective, multi: true }]
})
export class CustomAsyncValidatorDirective implements AsyncValidator {
    @Input("customAsyncValidator") public customAsyncValidator: AsyncValidatorFn | AsyncValidatorFn[];

    validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
        if (this.customAsyncValidator instanceof Function)
            return this.customAsyncValidator(control);

        return this.combineValidatorMethods(control, this.customAsyncValidator);
    }

    private combineValidatorMethods(control: AbstractControl, methods: AsyncValidatorFn[]): Observable<ValidationErrors | null> {
        return combineLatest(...methods.map(m => m(control))).pipe(map(s$ => {
            let errorObj: ValidationErrors = null;

            for (let validatorResult of s$) {
                if (errorObj === null)
                    errorObj = {};
                errorObj = Object.assign(errorObj, validatorResult);
            }

            return errorObj;
        }));
    }
}
