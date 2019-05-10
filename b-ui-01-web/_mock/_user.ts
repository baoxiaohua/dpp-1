import { MockRequest, MockStatusError } from '@delon/mock';
// TIPS: mockjs 一些优化细节见：https://ng-alain.com/docs/mock
// import * as Mock from 'mockjs';

const list = [];
const total = 50;

for (let i = 0; i < total; i += 1) {
  list.push({
    id: i + 1,
    disabled: i % 6 === 0,
    href: 'https://ant.design',
    avatar: [
      'https://gw.alipayobjects.com/zos/rmsportal/eeHMaZBwmTvLdIwMfBpg.png',
      'https://gw.alipayobjects.com/zos/rmsportal/udxAbMEhpwthVVcjLXik.png',
    ][i % 2],
    avatar1: [
      'https://pic.sogou.com/d?query=%CD%B7%CF%F1&st=191&mode=1&did=2#did1',
      'https://pic.sogou.com/d?query=%CD%B7%CF%F1&st=191&mode=1&did=2#did1',
    ][i % 2],
    no: `TradeCode ${i}`,
    title: `一个任务名称 ${i}`,
    owner: '曲丽丽',
    description: '这是一段描述',
    callNo: Math.floor(Math.random() * 1000),
    status: Math.floor(Math.random() * 10) % 4,
    updatedAt: new Date(`2017-07-${Math.floor(i / 2) + 1}`),
    createdAt: new Date(`2017-07-${Math.floor(i / 2) + 1}`),
    progress: Math.ceil(Math.random() * 100),
  });
}

function genData(params: any) {
  let ret = [...list];
  const pi = +params.pi,
    ps = +params.ps,
    start = (pi - 1) * ps;

  if (params.no) {
    ret = ret.filter(data => data.no.indexOf(params.no) > -1);
  }

  return { total: ret.length, list: ret.slice(start, ps * pi) };
}

function saveData(id: number, value: any) {
  const item = list.find(w => w.id === id);
  if (!item) {
    return { msg: '无效用户信息' };
  }
  Object.assign(item, value);
  return { msg: 'ok' };
}
// { title: '项目编号', index: 'id' },
// { title: '项目名称', index: 'projectName' },
// { title: '操作类型', index: 'type' },
// { title: '执行结果', index: 'status', render: 'status' },
// { title: '操作时间', type: 'date', index: 'staTime' },


const advancedOperation1 = [
  {
    idx: '021555',
    projectNe:'科研项目02',
    type: '项目创建',
    status: 'agree',
    updatedAt: '2017-10-03  19:23:12',
    memo: '确定需求',
  },
  {
    idx: '021555',
    projectNe:'科研项目02',
    type: '项目审核',
    status: 'agree',
    updatedAt: '2017-11-03  19:23:12',
    memo: '加快进度',
  },
  {
    idx: '0254615',
    projectNe:'科研项目12',
    type: '项目创建',
    status: 'agree',
    updatedAt: '2017-10-03  19:23:12',
    memo: '-',
  },
  {
    idx: '0214578',
    projectNe:'科研项目02',
    type: '项目交付',
    status: 'agree',
    updatedAt: '2018-10-03  19:23:12',
    memo: '-',
  },
  {
    idx: '021555',
    projectNe:'科研项目02',
    type: '项目创建',
    status: 'agree',
    updatedAt: '2017-10-03  19:23:12',
    memo: '确定需求',
  },
];
const advancedOperation2 = [
  {
    idx: '021555',
    projectNe:'科研项目02',
    type: '项目创建',
    status: 'agree',
    updatedAt: '2017-10-03  19:23:12',
    memo: '确定需求',
  },
];

const advancedOperation3 = [
  {
    idx: '021555',
    projectNe:'科研项目02',
    type: '项目创建',
    status: 'agree',
    updatedAt: '2017-10-03  19:23:12',
    memo: '确定需求',
  },
];

export const PROFILES = {
  'GET /detail-page/detail': {
    advancedOperation1,
    advancedOperation2,
    advancedOperation3,
  },
};

export const USERS = {
  '/user': (req: MockRequest) => genData(req.queryString),
  '/user/:id': (req: MockRequest) => list.find(w => w.id === +req.params.id),
  'POST /user/:id': (req: MockRequest) => saveData(+req.params.id, req.body),
  // 支持值为 Object 和 Array
  '/user/current': {
    name: '小小库',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png',
    userid: '12341234',
    email: 'xiaoxiaoku@qq.com',
    signature: '数据存储、数据计算、数据处理',
    title: '数据处理平台',
    group: '数据处理－－某某平台部－某某技术部－UED',
    tags:[
      {
        key:'0',
        label:'XX部门'
      },
      {
        key:'1',
        label:'XL部门'
      },
      {
        key:'2',
        label:'XM部门'
      },
      {
        key:'3',
        label:'XN部门'
      },
    ],
    notifyCount: 12,
    country: 'China',
    geographic: {
      province: {
        label: '上海',
        key: '330000',
      },
      city: {
        label: '市辖区',
        key: '330100',
      },
    },
    address: 'XX区XXX路 XX 号',
    phone: '12234567656',
  },
  'POST /user/avatar': 'ok',
  'POST /login/account': (req: MockRequest) => {
    const data = req.body;
    if (!(data.userName === 'admin' || data.userName === 'user') || data.password !== 'ng-alain.com') {
      return { msg: `Invalid username or password（admin/ng-alain.com）` };
    }
    return {
      msg: 'ok',
      user: {
        token: '123456789',
        name: data.userName,
        email: `${data.userName}@qq.com`,
        id: 10000,
        time: +new Date(),
      },
    };
  },
  'POST /register': {
    msg: 'ok',
  },
  'GET /users': { users: [1, 2], total: 2 },
  // GET 可省略
  // '/users/1': Mock.mock({ id: 1, 'rank|3': '★★★' }),
  // POST 请求
  'POST /users/1': { uid: 1 },
  // 获取请求参数 queryString、headers、body
  '/qs': (req: MockRequest) => req.queryString.pi,
  // 路由参数
  '/users/:id': (req: MockRequest) => req.params, // /users/100, output: { id: 100 }
  // 发送 Status 错误
  '/404': () => {
    throw new MockStatusError(404);
  },
};
