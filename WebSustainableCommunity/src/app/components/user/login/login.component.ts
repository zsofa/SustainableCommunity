import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TextBoxComponent } from '@progress/kendo-angular-inputs';
import { UserService } from 'src/app/services/User.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.None, 
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";
  public loggedin: boolean = false;

  constructor(
    public userService: UserService,
    public router: Router,
  ) { }



@ViewChild("loginPassword") public textbox: TextBoxComponent;
public ngAfterViewInit(): void {
  this.textbox.input.nativeElement.type = "password";
}

public toggleVisibility(): void {
  const inputEl = this.textbox.input.nativeElement;
  inputEl.type = inputEl.type === "password" ? "text" : "password";
}

public form: FormGroup = new FormGroup({
  username: new FormControl(),
  password: new FormControl(),
  loggedin: new FormControl(),
});

public login(): void {
  this.form.markAllAsTouched();
  this.userService.login(this.username,this.password);
 // this.router.navigateByUrl("/home")
  //  alert($localize `This must be translated`);   ez kell a typscripten belüli fordításhoz a ' $localize  '!!!
}

public clearForm(): void {
  this.form.reset();
}

  ngOnInit() {
  }


}
