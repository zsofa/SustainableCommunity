import {ChangeDetectorRef, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormControl,
  FormGroup,
  FormsModule,
  NgForm,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {TextBoxComponent} from '@progress/kendo-angular-inputs';
import {User} from "../../../models/User";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import { UserService } from 'src/app/services/User.service';
import { UserApiService } from 'src/app/services/UserApi.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class RegistrationComponent implements OnInit {

  constructor(
    public userService: UserService,
    public userApiService: UserApiService,
    changeDetector: ChangeDetectorRef,

  ) { }

  public user: User = new User();

  @ViewChild("form") form: NgForm;

  
  public clearForm(): void {
    this.form.reset();
  }

  @ViewChild("passwordTextbox") public textbox: TextBoxComponent;
  @ViewChild("passwordTextbox2") public textbox2: TextBoxComponent;

  public ngAfterViewInit(): void {
    this.textbox.input.nativeElement.type = "password";
    this.textbox2.input.nativeElement.type = "password";
  }

  public toggleVisibility(): void {
    const inputEl = this.textbox.input.nativeElement;
    inputEl.type = inputEl.type === "password" ? "text" : "password";
  }

  public toggleVisibilitytoPassAgain(): void {
    const inputEl = this.textbox2.input.nativeElement;
    inputEl.type = inputEl.type === "password" ? "text" : "password";
  }



  public onSubmit() {
    if (this.form.invalid) {
      return;
    }
    this.userApiService.register(this.user).subscribe(value => {
      console.log(value);
      alert("Success");
    });
  }


  checkUserNameIsUnique2 = (control: AbstractControl) => {
    return Promise.resolve({
      isUserNameUnique: true
    })
  }
  checkUserNameIsUnique = (control: AbstractControl) => {
    let user = this.form.value as User;
    return this.userApiService.isUserNameUnique(control.value).pipe(
      map((isUserNameUnique) => {
        if (isUserNameUnique) {
          return null;
        } else {
          return {
            isUserNameUnique: true
          }
        }
      })
    )
  }

  checkUserNameIsUnique3 = (password: string, passwordRepeat: string): AsyncValidatorFn => {
    // [customAsyncValidator]="checkUserNameIsUnique3(form.value.password, form.value.passAgain)"
    return (control) => {
      return this.userApiService.isUserNameUnique(password).pipe(
        map((isUserNameUnique) => {
          if (isUserNameUnique) {
            return null;
          } else {
            return {
              isUserNameUnique: true
            }
          }
        })
      )
    }
  }

  equalsPassAgain: ValidatorFn = (control: AbstractControl) => {
    let password = control.value;
    let passAgain = this.user.passAgain;
//control.markAsTouched()
    if (passAgain === password) {
      return null;
    } else {
      return {
        equalsPassAgain: true
      }
    }


  }
//  equalsPassword = (control: AbstractControl): any => {
  equalsPassword:ValidatorFn = (control: AbstractControl) => {
    let password = this.user.password;
    let passAgain = control.value;
    if (passAgain === password) {
      return null;
    } else {
      return {
        equalsPassword: true
      }
    }

  }

  ngOnInit() {
  }

}
