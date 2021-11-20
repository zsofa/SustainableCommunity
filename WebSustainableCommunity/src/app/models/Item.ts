import { User } from "./User";

export class Item {
    id:number;
    name:string;
    description: string;
    isBorrowed:boolean;
    owner:User;

}
