import { Injectable } from '@angular/core';
import { NzMessageDataOptions, NzMessageDataFilled, NzMessageService } from 'ng-zorro-antd';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class I18nNzMessageService {
    constructor(
        private msg: NzMessageService,
        private translateService: TranslateService,
        ) {
    }

    success(content: string, params?: any, options?: NzMessageDataOptions): any {
        return this.translateService.get(content, params).subscribe(text => {
            return this.msg.success(text, options);
        });
    }
    error(content: string, params?: any, options?: NzMessageDataOptions): any {
        return this.translateService.get(content, params).subscribe(text => {
            return this.msg.error(text, options);
        });
    }
    info(content: string, params?: any, options?: NzMessageDataOptions): any {
        return this.translateService.get(content, params).subscribe(text => {
            return this.msg.info(text, options);
        });
    }
    warning(content: string, params?: any, options?: NzMessageDataOptions): any {
        return this.translateService.get(content, params).subscribe(text => {
            return this.msg.warning(text, options);
        });
    }
    loading(content: string, params?: any, options?: NzMessageDataOptions): any {
        return this.translateService.get(content, params).subscribe(text => {
            return this.msg.loading(text, options);
        });
    }

    // error(content: string, options?: NzMessageDataOptions): NzMessageDataFilled;
    // info(content: string, options?: NzMessageDataOptions): NzMessageDataFilled;
    // warning(content: string, options?: NzMessageDataOptions): NzMessageDataFilled;
    // loading(content: string, options?: NzMessageDataOptions): NzMessageDataFilled;
    // create(type: 'success' | 'info' | 'warning' | 'error' | 'loading' | string,
    //     content: string, options?: NzMessageDataOptions): NzMessageDataFilled;
}
