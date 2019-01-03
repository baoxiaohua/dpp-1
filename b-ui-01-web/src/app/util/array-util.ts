export const switchArrayElements = (array: any[], index1: number, index2: number): any[] => {
  if (index1 < 0 || index2 < 0) return array;
  if (index1 >= array.length || index2 >= array.length) return array;

  const tmpArray = array.map(item => Object.assign({}, item));

  const tmp = array[index1];
  tmpArray[index1] = array[index2];
  tmpArray[index2] = tmp;

  return tmpArray;
};
