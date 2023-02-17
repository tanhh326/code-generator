<template>
  <div class="container">
    <a-card class="general-card" title="查询表格">
      <a-row>
        <a-col :flex="1">
          <a-form
              :label-col-props="{ span: 6 }"
              :model="queryRequest"
              :wrapper-col-props="{ span: 18 }"
              label-align="left"
          >
            <a-row :gutter="16">
              <a-col :span="8">
                <a-form-item
                    field="number"
                    label="集合编号"
                >
                  <a-input
                      v-model="queryRequest.number"
                      placeholder="请输入集合编号"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item field="name" label="集合名称">
                  <a-input
                      v-model="queryRequest.name"
                      placeholder="请输入集合名称"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item
                    field="createdTime"
                    label="创建时间"
                >
                  <a-range-picker
                      v-model="queryRequest.createdTime"
                      style="width: 100%"
                  />
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
        <a-divider direction="vertical" style="height: 84px"/>
        <a-col :flex="'86px'" style="text-align: right">
          <a-space :size="18" direction="vertical">
            <a-button type="primary" @click="search">
              <template #icon>
                <icon-search/>
              </template>
              查询
            </a-button>
            <a-button @click="reset">
              <template #icon>
                <icon-refresh/>
              </template>
              重置
            </a-button>
          </a-space>
        </a-col>
      </a-row>
      <a-divider style="margin-top: 0"/>
      <a-row style="margin-bottom: 16px">
        <a-col :span="12">
          <a-space>
            <a-button type="primary">
              <template #icon>
                <icon-plus/>
              </template>
              新增
            </a-button>

          </a-space>
        </a-col>
        <a-col
            :span="12"
            style="display: flex; align-items: center; justify-content: end"
        >
        </a-col>
      </a-row>
      <a-table
          :bordered="false"
          :columns="columns"
          :data="response"
          :loading="loading"
          :pagination="pagination"
          :size="size"
          row-key="id"
          @page-change="onPageChange"
      >
        <template #index="{ rowIndex }">
          {{ rowIndex + 1 + (pagination.current - 1) * pagination.pageSize }}
        </template>
        <template #operations="{ record }">
          <a-button size="small" type="text" @click="show(record)">
            查看
          </a-button>
          <a-button size="small" type="text" @click="update(record)">
            修改
          </a-button>
          <a-button size="small" type="text" @click="delte(record)">
            删除
          </a-button>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {computed, onMounted, reactive, ref} from 'vue';
import useLoading from '@/hooks/loading';
import {Pagination} from '@/types/global';
import type {TableColumnData} from '@arco-design/web-vue/es/table/interface';

type SizeProps = 'mini' | 'small' | 'medium' | 'large';
type Column = TableColumnData & { checked?: true };

// 外部
const generateFormModel = () => {
  return {
    number: '',
    name: '',
    contentType: '',
    filterType: '',
    createdTime: [],
    status: '',
  };
};
const {loading, setLoading} = useLoading(true);
const response = ref([]);
const queryRequest = ref(generateFormModel());

const size = ref<SizeProps>('medium');

// 查看所需调用接口
const show = (recode: any) => {
  console.log(recode);
}
// 更新所需调用接口
const update = (recode: any) => {
  console.log(recode);
}
// 删除所修调用接口
const delte = (recode: any) => {
  console.log(recode);
}
const basePagination: Pagination = {
  current: 1,
  pageSize: 20,
};
const pagination = reactive({
  ...basePagination,
});

// 需要显示的字段
const columns = computed<TableColumnData[]>(() => [
  {
    title: "#",
    dataIndex: 'index',
    slotName: 'index',
  },
  {
    title: "编号集合",
    dataIndex: 'number',
  },
  {
    title: "操作列",
    dataIndex: 'operations',
    slotName: 'operations',
  },
]);

// 搜索接口
const fetchData = () => {
  setLoading(true);
  try {
    response.value = [
      {
        "id": "123",
        "index": "asf",

      }
    ];
    pagination.current = 0;
    pagination.total = 100;
  } catch (err) {
  } finally {
    setLoading(false);
  }
};


// 搜索接口
const search = () => {

};
// 当页码发送变化时处理的接口
const onPageChange = (current: number) => {
};

// 查询条件清空的情况
const reset = () => {
};


onMounted(() => {
  fetchData()
})


</script>

<script lang="ts">
export default {
  name: 'MonoTable',
};
</script>

<style lang="less" scoped>
.container {
  padding: 0 20px 20px 20px;
}

:deep(.arco-table-th) {
  &:last-child {
    .arco-table-th-item-title {
      margin-left: 16px;
    }
  }
}

.action-icon {
  margin-left: 12px;
  cursor: pointer;
}

.active {
  color: #0960bd;
  background-color: #e3f4fc;
}

.setting {
  display: flex;
  align-items: center;
  width: 200px;

  .title {
    margin-left: 12px;
    cursor: pointer;
  }
}
</style>
