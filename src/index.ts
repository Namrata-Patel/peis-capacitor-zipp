import { registerPlugin } from '@capacitor/core';

import type { ZipHelpPlugin } from './definitions';

const ZipHelp = registerPlugin<ZipHelpPlugin>('ZipHelp', {
  web: () => import('./web').then(m => new m.ZipHelpWeb()),
});

export * from './definitions';
export { ZipHelp };
