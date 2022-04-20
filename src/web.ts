import { WebPlugin } from '@capacitor/core';

import type { UnzipOptions, UnzipResult, ZipHelpPlugin } from './definitions';

export class ZipHelpWeb extends WebPlugin implements ZipHelpPlugin {
  async unZip(options: UnzipOptions): Promise<UnzipResult> {
    console.log('Unzip', options);
    return Promise.reject(new Error('Only available on android'));
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
