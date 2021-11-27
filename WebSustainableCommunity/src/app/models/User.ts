export enum UserRole {
    _name = <any>"UserRole",

    CUSTOMER = 1,
    ADMIN,

    _labels = <any>[
        { value: UserRole.CUSTOMER, label: "Customer" },
        { value: UserRole.ADMIN, label: "Admin" },
    ]
}

export class User {

    id: number;

    email: string = "";
    username: string = "";
    password: string = "";
    passAgain: string= "";
    userRole: UserRole;

}
