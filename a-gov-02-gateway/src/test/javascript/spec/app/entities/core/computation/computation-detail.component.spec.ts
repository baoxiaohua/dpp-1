/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ComputationDetailComponent } from 'app/entities/core/computation/computation-detail.component';
import { Computation } from 'app/shared/model/core/computation.model';

describe('Component Tests', () => {
    describe('Computation Management Detail Component', () => {
        let comp: ComputationDetailComponent;
        let fixture: ComponentFixture<ComputationDetailComponent>;
        const route = ({ data: of({ computation: new Computation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ComputationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ComputationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComputationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.computation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
