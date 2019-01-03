import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ISystemQuery } from 'app/model/core/system-query.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<ISystemQuery>;
type EntityArrayResponseType = HttpResponse<ISystemQuery[]>;

@Injectable({ providedIn: 'root' })
export class SystemQueryCustomService {
    public resourceUrl = '/core/api/system-queries';

    constructor(private http: HttpClient) {}

    query(req?: any, criteria?: any): Observable<HttpResponse<any>> {
      const options = createRequestOption(req);
      return this.http.post(`${this.resourceUrl}/query`, criteria || {}, {
        params: options,
        observe: 'response',
      });
    }
}
