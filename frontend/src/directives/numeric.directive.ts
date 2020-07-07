import {Directive, ElementRef, HostBinding, HostListener, ViewChild} from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl } from '@angular/forms';

@Directive({
  selector: '[appNumeric]',
  providers: [{provide: NG_VALIDATORS, useExisting: NumericDirective, multi: true}]
})
export class NumericDirective implements Validator{

  constructor(private element:ElementRef) { }

  public validate(control: AbstractControl): {[key: string]: any} {
    let currencyRegEx = /^(([0])|([1-9][0-9]*))\.?[0-9][0-9]$/;
    let valid = currencyRegEx.test(control.value);
    return valid ? null : {'appNumeric': true};
  }
}
