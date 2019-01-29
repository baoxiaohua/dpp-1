import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  public contentBodyWidth = 1920;
  // public contentBodyHeight = 1080;
  public contentHeightOffset = 199;

  constructor() { }
}
