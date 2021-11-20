import { Injectable } from '@angular/core';
import { Item } from '../models/Item';


@Injectable({
  providedIn: 'root'
})
export class ItemService {
  
  public items: Item[] = null; // igy legenerál nekem mindig tesztalanyokat

constructor() {

  if (!this.items) {    // ez csak addig kell amig nincs adatbázis, utána ez kuka.
    this.items = [
        { id: 1, name: "szék", description: "Blue, midsize",isBorrowed:false, owner: null },
        { id: 2, name: "asztal", description: "White, Wood", isBorrowed: false,owner: null },
        { id: 3, name: "szekrény",description: "Black, Plastic", isBorrowed: false,owner: null },
    ];
}


 }

 getProductById(id: number): Item {
  for (let i = 0; i < this.items.length; i++) {
          if (this.items[i].id === id) {
              return this.items[i];
          }
    
      
  }
  return null;

}



}
