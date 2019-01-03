/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ComputationGroupService } from 'app/entities/core/computation-group/computation-group.service';
import { IComputationGroup, ComputationGroup } from 'app/shared/model/core/computation-group.model';

describe('Service Tests', () => {
    describe('ComputationGroup Service', () => {
        let injector: TestBed;
        let service: ComputationGroupService;
        let httpMock: HttpTestingController;
        let elemDefault: IComputationGroup;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ComputationGroupService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ComputationGroup(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
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

            it('should create a ComputationGroup', async () => {
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
                    .create(new ComputationGroup(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ComputationGroup', async () => {
                const returnedFromService = Object.assign(
                    {
                        identifier: 'BBBBBB',
                        name: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
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

            it('should return a list of ComputationGroup', async () => {
                const returnedFromService = Object.assign(
                    {
                        identifier: 'BBBBBB',
                        name: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTs: currentDate.format(DATE_TIME_FORMAT),
                        updateTs: currentDate.format(DATE_TIME_FORMAT),
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

            it('should delete a ComputationGroup', async () => {
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
