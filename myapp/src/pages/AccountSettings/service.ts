import { request } from 'umi';
import type { CurrentUser, GeographicItemType } from './data';

export async function queryCurrent(): Promise<{ data: CurrentUser }> {
  return request('/api/accountSettingCurrentUser');
}

export async function query() {
  return request('/api/users');
}
