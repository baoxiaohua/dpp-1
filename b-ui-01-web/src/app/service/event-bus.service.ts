import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventBusService {

  public queue = new BehaviorSubject(null);

  constructor() { }
}
