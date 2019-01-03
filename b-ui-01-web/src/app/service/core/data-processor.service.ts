import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IDataProcessor } from 'app/model/core/data-processor.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IDataProcessor>;
type EntityArrayResponseType = HttpResponse<IDataProcessor[]>;

@Injectable({ providedIn: 'root' })
export class DataProcessorService {
    public resourceUrl = '/core/api/data-processors';

    constructor(private http: HttpClient) {}

    create(dataProcessor: IDataProcessor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dataProcessor);
        return this.http
            .post<IDataProcessor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dataProcessor: IDataProcessor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dataProcessor);
        return this.http
            .put<IDataProcessor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDataProcessor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDataProcessor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dataProcessor: IDataProcessor): IDataProcessor {
        const copy: IDataProcessor = Object.assign({}, dataProcessor, {
            createTs: dataProcessor.createTs != null && dataProcessor.createTs.isValid() ? dataProcessor.createTs.toJSON() : null,
            updateTs: dataProcessor.updateTs != null && dataProcessor.updateTs.isValid() ? dataProcessor.updateTs.toJSON() : null
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
            res.body.forEach((dataProcessor: IDataProcessor) => {
                dataProcessor.createTs = dataProcessor.createTs != null ? moment(dataProcessor.createTs) : null;
                dataProcessor.updateTs = dataProcessor.updateTs != null ? moment(dataProcessor.updateTs) : null;
            });
        }
        return res;
    }
}
