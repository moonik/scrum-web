import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filter'
})
export class SearchPipe implements PipeTransform {
  transform(items: any[], criteria: string): any {

    if (!criteria) {
      return [];
    }

    criteria = criteria.toLocaleLowerCase();

    return items.filter(item => {
      for (const key in item) {
        if (('' + item[key]).includes(criteria)) {
          return true;
        }
      }
      return false;
    });
  }
}
