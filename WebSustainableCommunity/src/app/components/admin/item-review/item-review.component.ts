import { Component, OnInit } from '@angular/core';
import { ItemService } from 'src/app/services/Item.service';
import { UserService } from 'src/app/services/User.service';

@Component({
  selector: 'app-item-review',
  templateUrl: './item-review.component.html',
  styleUrls: ['./item-review.component.scss']
})
export class ItemReviewComponent implements OnInit {

  constructor(
    public itemService: ItemService,
    public userService : UserService,
  ) { }

  ngOnInit() {
  }

}
