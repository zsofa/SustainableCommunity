import { Injectable } from '@angular/core';
import { Category, Item } from '../models/Item';


@Injectable({
  providedIn: 'root'
})
export class ItemService {
  
  public items: Item[] = null; // igy legenerál nekem mindig tesztalanyokat

constructor() {

//   if (!this.items) {    // ez csak addig kell amig nincs adatbázis, utána ez kuka.
//     this.items = [
//         { id: 1, itemName: "szék",itemCategory:Category.ADVENTURING, description: "Blue, midsize",isBorrowed:false, owner: null },
//         { id: 2, itemName: "asztal",itemCategory:Category.ADVENTURING, description: "White, Wood", isBorrowed: false,owner: null },
//         { id: 3, itemName: "szekrény",itemCategory:Category.ADVENTURING,description: "Black, Plastic", isBorrowed: false,owner: null },
//     ];
// }


 }

 getProductById(id: number): Item {
  for (let i = 0; i < this.items.length; i++) {
          if (this.items[i].itemId === id) {
              return this.items[i];
          }
    
      
  }
  return null;

}



}
