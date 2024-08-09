import { registerPlugin } from '@capacitor/core';

import type { SharetoPlugin } from './definitions';

const Shareto = registerPlugin<SharetoPlugin>('Shareto', {
  web: () => import('./web').then(m => new m.SharetoWeb()),
});

export * from './definitions';
export { Shareto };
