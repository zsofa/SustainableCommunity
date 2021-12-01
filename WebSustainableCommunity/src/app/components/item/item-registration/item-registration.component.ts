import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Condition,Category, Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/services/Item.service';
import { ItemApiService } from 'src/app/services/ItemApi.service';
import { UserService } from 'src/app/services/User.service';

@Component({
  selector: 'app-item-registration',
  templateUrl: './item-registration.component.html',
  styleUrls: ['./item-registration.component.scss']
})
export class ItemRegistrationComponent implements OnInit {

  constructor(
    public itemService: ItemService,
    public itemApiService: ItemApiService,
    public userService : UserService
  ) { }

  public item: Item = new Item();
  public Category = Category;
  public Condition = Condition;
  public  format = "0.00 cm";

  @ViewChild("form") form: NgForm;

  public onSubmit() {
    if (this.form.invalid) {
      return;
    }
    this.itemSendToReview();
    this.itemApiService.register(this.item).subscribe(value => {
      console.log(value);
      //alert("Success"); // ez csak tesztelésre, majd szedd ki
    });
  }
    //ez itt jelenleg nem müxik
    //  ezzel próbálkoztam html oldalon  [defaultItem]="defaultItem"
  public defaultItem:{
    text: "Select item...",
    value: null,

  //  placeholder = "Select..."

  };

public itemSendToReview(){
  if(confirm("Upload request forward to the checkpoint?")){
  let userID = this.userService.currentUser.id; // ide majd a beloggolt/current user kell amihez kell majd 1 User import
  let uploadedItem = this.item;
  this.itemService.itemReview(userID,uploadedItem);
  }
 // this.itemService.itemReview(userID,uploadedItem);
}
  


  
  // public test(clickevent){
  //   console.log(clickevent)
  // }




  ngOnInit() {
  }

}
