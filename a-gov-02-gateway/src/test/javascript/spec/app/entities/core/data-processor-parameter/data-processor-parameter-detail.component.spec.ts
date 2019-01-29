/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorParameterDetailComponent } from 'app/entities/core/data-processor-parameter/data-processor-parameter-detail.component';
import { DataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';

describe('Component Tests', () => {
    describe('DataProcessorParameter Management Detail Component', () => {
        let comp: DataProcessorParameterDetailComponent;
        let fixture: ComponentFixture<DataProcessorParameterDetailComponent>;
        const route = ({ data: of({ dataProcessorParameter: new DataProcessorParameter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorParameterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataProcessorParameterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataProcessorParameterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataProcessorParameter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
