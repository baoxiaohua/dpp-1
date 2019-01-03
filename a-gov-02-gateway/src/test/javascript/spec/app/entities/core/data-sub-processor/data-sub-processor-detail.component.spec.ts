/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataSubProcessorDetailComponent } from 'app/entities/core/data-sub-processor/data-sub-processor-detail.component';
import { DataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';

describe('Component Tests', () => {
    describe('DataSubProcessor Management Detail Component', () => {
        let comp: DataSubProcessorDetailComponent;
        let fixture: ComponentFixture<DataSubProcessorDetailComponent>;
        const route = ({ data: of({ dataSubProcessor: new DataSubProcessor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataSubProcessorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataSubProcessorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataSubProcessorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataSubProcessor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
