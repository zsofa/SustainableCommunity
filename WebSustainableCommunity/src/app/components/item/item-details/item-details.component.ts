import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/services/Item.service';
import { UserService } from 'src/app/services/User.service';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.scss']
})
export class ItemDetailsComponent implements OnInit {

  constructor(
    public itemService: ItemService,
    public userService: UserService,
    private route: ActivatedRoute
  ) { }


  public currentItem: Item | undefined;

  ngOnInit() {
    const routeParams = this.route.snapshot.paramMap;
    const productIdFromRoute = Number(routeParams.get('id'));

   this.currentItem = this.itemService.getProductById(productIdFromRoute);

  }

}
