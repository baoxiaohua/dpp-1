import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Injectable({providedIn: 'root'})
export class I18nObjectService {
  constructor(private translateService: TranslateService) {
  }

  getI18n(object) {
    const pattern = new RegExp('\\$\\{[a-zA-Z0-9\\.\\-]*\\}', 'g');
    let temp = JSON.stringify(object);

    const match = JSON.stringify(object).match(pattern);
    if (!!match) {
      match.forEach(item => {
        // item => {app.some.key}
        const key = item.substr(2, item.length - 3); // key => app.some.key
        temp = temp.replace(item, this.translateService.instant(key));
      });
    }
    return JSON.parse(temp);
  }
}
