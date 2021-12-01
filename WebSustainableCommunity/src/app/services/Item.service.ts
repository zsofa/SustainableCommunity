import { Injectable } from '@angular/core';
import { Category, Item } from '../models/Item';
import { ItemUser } from '../models/ItemUser';


@Injectable({
  providedIn: 'root'
})
export class ItemService {
  
  public items: Item[] = null; // igy legenerál nekem mindig tesztalanyokat
  public approveList: Item[] = [];

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
  for (let i = 0; i < this.approveList.length; i++) {
          if (this.approveList[i].itemId === id) {
              return this.approveList[i];
          }
  }
  return null;

}




// nem jó ha itemUser adok át a nagy listába, mert nekem mindig 1 itemet kellene megvizsgálni, nem pedig a user összes itemét

public itemReview(userId: number, item: Item) { //userID a beloggolt user id-ja
  let upload = new ItemUser(); // ide kell a reguser  (currentuser)

  let maxId = 0;
  for (let i = 0; i < upload.itemList.length; i++) { //this reguser.itemlist -je kellene ide,
    if (upload.itemList[i].itemId > maxId) {
      maxId = upload.itemList[i].itemId
    }
  }

  upload.userId = userId;
  upload.itemId = maxId + 1;
  upload.itemList.push(item);   //feltöltött item   
 // upload.date = new Date(Date.now());
 

  this.approveList.push(item);   // az approveList lesz az admin felület 
  console.log(upload);
  console.log(item);
  // az this.approveList.push(upload);-nak simán csak itemet kéne befogadnia, és item-ben benne van a owner, az alapján adni vissza

  // this.productService.clearCart();
  // console.log(this.orders);
}


}
