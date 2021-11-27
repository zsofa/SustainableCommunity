import { User } from "./User";


export enum Category {
    _name = <any>"Category",

    ADVENTURING = 0,
    CLEANING,
    COOKING_HOSTING,
    DIY,
    HOBBY,
    GARDENING,

    _labels = <any>[
        { value: Category.ADVENTURING, label: $localize `Adventuring` },
        { value: Category.CLEANING, label: "Cleaning" },
        { value: Category.COOKING_HOSTING, label: "Cooking"},
        { value: Category.DIY, label: "Diy"},
        { value: Category.HOBBY, label: "Hobby"},
        { value: Category.GARDENING, label: "Gardening"},

    ]
}


export enum Condition {
    _name = <any>"Condition",

    NEW = 0,
    VERY_GOOD,
    GOOD,
    FAIR,
    POOR,

    _labels = <any>[
        { value: Condition.NEW, label: "New" },
        { value: Condition.VERY_GOOD, label: "Very good" },
        { value: Condition.GOOD, label: "Good"},
        { value: Condition.FAIR, label: "Fair"},
        { value: Condition.POOR, label: "Poor"},

    ]
}


export class Item {
    itemId:number;
    itemName:string = "";
    itemCategory: Category;
    itemCondition: Condition;
    itemValue:number;
    borrowPrice:number;
    description:string = "";
    itemHeight:number = null;
    itemWidth:number = null;
    isAvailable:boolean;
    isApproved:boolean;
    owner:User;

}
