import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const GENERATOR: AppRouteRecordRaw = {
  path: '/generator',
  name: 'generator',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: '生成器测试',
    icon: 'icon-generator',
    requiresAuth: true,
    order: 7,
  },
  children: [
    {
      path: 'company',
      name: 'Company',
      component: () => import('@/views/generator/company/index.vue'),
      meta: {
        locale: '企业管理',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: 'dept',
      name: 'Dept',
      component: () => import('@/views/generator/dept/index.vue'),
      meta: {
        locale: '部门管理',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: 'post',
      name: 'Post',
      component: () => import('@/views/generator/post/index.vue'),
      meta: {
        locale: '岗位管理',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: 'user',
      name: 'User',
      component: () => import('@/views/generator/user/index.vue'),
      meta: {
        locale: '用户管理',
        requiresAuth: true,
        roles: ['*'],
      },
    },
  ],
};

export default GENERATOR;
