/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ComputationService } from 'app/entities/core/computation/computation.service';
import { IComputation, Computation, ComputationType, ComputationStatus } from 'app/shared/model/core/computation.model';

describe('Service Tests', () => {
    describe('Computation Service', () => {
        let injector: TestBed;
        let service: ComputationService;
        let httpMock: HttpTestingController;
        let elemDefault: IComputation;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ComputationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Computation(
                0,
                'AAAAAAA',
                'AAAAAAA',
                ComputationType.KYLIN_SQL,
                ComputationStatus.DRAFT,
                'AAAAAAA',
                currentDate,
                currentDate,
                0,
                false
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

            it('should create a Computation', async () => {
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
                    .create(new Computation(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Computation', async () => {
                const returnedFromService = Object.assign(
                    {
                        identifier: 'BBBBBB',
                        name: 'BBBBBB',
                        type: 'BBBBBB',
                        status: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
                        computationGroupId: 1,
                        deleted: true
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

            it('should return a list of Computation', async () => {
                const returnedFromService = Object.assign(
                    {
                        identifier: 'BBBBBB',
                        name: 'BBBBBB',
                        type: 'BBBBBB',
                        status: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
                        computationGroupId: 1,
                        deleted: true
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

            it('should delete a Computation', async () => {
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
