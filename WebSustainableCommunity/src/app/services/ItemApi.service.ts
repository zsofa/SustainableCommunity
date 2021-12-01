import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Item} from "../models/Item";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ItemApiService {

constructor(
  private http: HttpClient

) { }

public register(newItem: Item): Observable<Item> {
  return this.http.post<Item>(environment.serverBaseHref + "/item/create", newItem)
}


}
