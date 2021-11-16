import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InputsModule } from '@progress/kendo-angular-inputs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonsModule } from '@progress/kendo-angular-buttons';
import { DialogsModule } from '@progress/kendo-angular-dialog';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { GridModule } from '@progress/kendo-angular-grid';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { KendoGridMultiSelectionDirective } from './directives/kendo-grid-multi-selection.directive';
import { KendoGridSingleSelectionDirective } from './directives/kendo-grid-single-selection.directive';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './components/user/login/login.component';
import { RegistrationComponent } from './components/user/registration/registration.component';
import { DateInputsModule } from '@progress/kendo-angular-dateinputs';
import { LabelModule } from '@progress/kendo-angular-label';
import { LayoutModule } from '@progress/kendo-angular-layout';
 import {HttpClientModule} from "@angular/common/http";
 import {CustomAsyncValidatorDirective} from "./directives/custom-async-validator.directive";
 import {CustomValidatorDirective} from "./directives/custom-validator.directive";

@NgModule({
  declarations: [
    AppComponent,
    KendoGridMultiSelectionDirective,
    KendoGridSingleSelectionDirective,
    LoginComponent,
    RegistrationComponent,
    CustomAsyncValidatorDirective,
    CustomValidatorDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    InputsModule,
    BrowserAnimationsModule,
    ButtonsModule,
    DialogsModule,
    DropDownsModule,
    GridModule,
    NgbModule,
    InputsModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    DateInputsModule,
    LabelModule,
    LayoutModule,
    HttpClientModule,


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
