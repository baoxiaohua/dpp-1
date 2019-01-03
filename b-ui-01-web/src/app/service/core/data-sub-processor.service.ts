import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IDataSubProcessor } from 'app/model/core/data-sub-processor.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IDataSubProcessor>;
type EntityArrayResponseType = HttpResponse<IDataSubProcessor[]>;

@Injectable({ providedIn: 'root' })
export class DataSubProcessorService {
    public resourceUrl = '/core/api/data-sub-processors';

    constructor(private http: HttpClient) {}

    create(dataSubProcessor: IDataSubProcessor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dataSubProcessor);
        return this.http
            .post<IDataSubProcessor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dataSubProcessor: IDataSubProcessor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dataSubProcessor);
        return this.http
            .put<IDataSubProcessor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDataSubProcessor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDataSubProcessor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dataSubProcessor: IDataSubProcessor): IDataSubProcessor {
        const copy: IDataSubProcessor = Object.assign({}, dataSubProcessor, {
            createTs: dataSubProcessor.createTs != null && dataSubProcessor.createTs.isValid() ? dataSubProcessor.createTs.toJSON() : null,
            updateTs: dataSubProcessor.updateTs != null && dataSubProcessor.updateTs.isValid() ? dataSubProcessor.updateTs.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createTs = res.body.createTs != null ? moment(res.body.createTs) : null;
            res.body.updateTs = res.body.updateTs != null ? moment(res.body.updateTs) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((dataSubProcessor: IDataSubProcessor) => {
                dataSubProcessor.createTs = dataSubProcessor.createTs != null ? moment(dataSubProcessor.createTs) : null;
                dataSubProcessor.updateTs = dataSubProcessor.updateTs != null ? moment(dataSubProcessor.updateTs) : null;
            });
        }
        return res;
    }
}
