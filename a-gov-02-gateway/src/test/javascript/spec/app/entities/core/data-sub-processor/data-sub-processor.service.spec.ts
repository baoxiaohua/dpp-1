/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DataSubProcessorService } from 'app/entities/core/data-sub-processor/data-sub-processor.service';
import { IDataSubProcessor, DataSubProcessor, DataProcessorType } from 'app/shared/model/core/data-sub-processor.model';

describe('Service Tests', () => {
    describe('DataSubProcessor Service', () => {
        let injector: TestBed;
        let service: DataSubProcessorService;
        let httpMock: HttpTestingController;
        let elemDefault: IDataSubProcessor;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DataSubProcessorService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new DataSubProcessor(
                0,
                0,
                'AAAAAAA',
                0,
                DataProcessorType.SQL_INTERIM,
                'AAAAAAA',
                false,
                false,
                false,
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a DataSubProcessor', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createTs: currentDate,
                        updateTs: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new DataSubProcessor(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a DataSubProcessor', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataProcessorId: 1,
                        name: 'BBBBBB',
                        sequence: 1,
                        dataProcessorType: 'BBBBBB',
                        code: 'BBBBBB',
                        outputAsTable: true,
                        outputAsObject: true,
                        outputAsResult: true,
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        createBy: 'BBBBBB',
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
                        updateBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createTs: currentDate,
                        updateTs: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of DataSubProcessor', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataProcessorId: 1,
                        name: 'BBBBBB',
                        sequence: 1,
                        dataProcessorType: 'BBBBBB',
                        code: 'BBBBBB',
                        outputAsTable: true,
                        outputAsObject: true,
                        outputAsResult: true,
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        createBy: 'BBBBBB',
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
                        updateBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createTs: currentDate,
                        updateTs: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a DataSubProcessor', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
