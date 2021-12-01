import { Component, OnInit } from '@angular/core';
import { ItemService } from 'src/app/services/Item.service';
import { UserService } from 'src/app/services/User.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    public userService: UserService,
    public itemService: ItemService
  ) { }


  public isUserAdmin(): boolean {
    return this.userService.isUserAdmin();
   }


  ngOnInit() {
  }

}
