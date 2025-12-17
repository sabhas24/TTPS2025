import {
  Component,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  ElementRef,
  AfterViewInit
} from '@angular/core';
import * as L from 'leaflet';

/* FIX iconos Leaflet */
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'leaflet/marker-icon-2x.png',
  iconUrl: 'leaflet/marker-icon.png',
  shadowUrl: 'leaflet/marker-shadow.png',
});

@Component({
  selector: 'app-mapa',
  standalone: true,
  template: `
    <div #mapContainer style="height:300px;width:100%;border-radius:8px"></div>
  `
})
export class MapaComponent implements AfterViewInit {

  private _lat!: number;
  private _lon!: number;

  @Input()
  set lat(value: number) {
    this._lat = value;
    this.updateMap();
  }

  @Input()
  set lon(value: number) {
    this._lon = value;
    this.updateMap();
  }

  @Output()
  coordenadasSeleccionadas = new EventEmitter<{ lat: number; lon: number }>();

  @ViewChild('mapContainer', { static: true })
  mapContainer!: ElementRef<HTMLDivElement>;

  private map!: L.Map;
  private marker!: L.Marker;
  private initialized = false;

  ngAfterViewInit(): void {
    this.map = L.map(this.mapContainer.nativeElement)
      .setView([this._lat, this._lon], 14);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    this.marker = L.marker([this._lat, this._lon]).addTo(this.map);

    this.map.on('click', (e: L.LeafletMouseEvent) => {
      const { lat, lng } = e.latlng;
      this.marker.setLatLng([lat, lng]);
      this.coordenadasSeleccionadas.emit({ lat, lon: lng });
    });

    this.initialized = true;
    setTimeout(() => this.map.invalidateSize(), 0);
  }

  private updateMap(): void {
    if (!this.initialized) return;

    console.log('Mapa actualizado a:', this._lat, this._lon);

    this.map.setView([this._lat, this._lon], 14);
    this.marker.setLatLng([this._lat, this._lon]);
  }
}
