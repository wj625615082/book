<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-plus"
        @click="handleAdd"
      >
        新增
      </el-button>
      <el-input
        v-model="listQuery.title"
        placeholder="书名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      >
        <!-- 添加清空按钮 -->
        <template #suffix>
          <i
            v-if="listQuery.title"
            class="el-icon-circle-close"
            style="cursor: pointer; color: #c0c4cc;"
            @click="clearInput"
          ></i>
        </template>
      </el-input>
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >
        搜索
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="书号" width="95">
        <template slot-scope="scope">
          {{ scope.row.isbn }}
        </template>
      </el-table-column>
      <el-table-column label="书名">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="作者" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出版社" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.publisher }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_time" label="入库时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <!-- 格式化日期 -->
          <span>{{ formatDate(scope.row.registerDate) }}</span>
        </template>
      </el-table-column>
      <!-- 添加操作列 -->
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.row)"
          >
            修改
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.size"
      :page-sizes="[5, 10, 15]"
      @pagination="fetchData"
    />

    <!-- 弹出框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="50%"
      @close="handleClose"
    >
      <el-form ref="editForm" :model="editForm" label-width="120px">
        <el-form-item label="书号">
          <el-input v-model="editForm.isbn" />
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="editForm.title" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="editForm.author" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="editForm.publisher" />
        </el-form-item>
        <el-form-item label="出版时间">
          <el-col :span="11">
            <el-date-picker v-model="editForm.publish_date" type="date" placeholder="请输入出版日期" style="width: 100%;" />
          </el-col>
        </el-form-item>
        <el-form-item label="图书简介">
          <el-input v-model="editForm.description" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保存</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getList, saveBook, updateBook, deleteBook } from '@/api/book'
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      statusMap: {
        1: 'draft',
        2: 'deleted',
        3: 'published'
      },
      listQuery: {
        title: "",
        author: "",
        status: 1,
        page: 1,
        size: 5
      },
      dialogVisible: false,
      editForm: {
        id: '',
        isbn: '',
        title: '',
        author: '',
        publisher: '',
        publish_date: '',
        description: ''
      },
      mode: 'add' // 默认是新增模式
    }
  },
  created() {
    this.fetchData()
  },
  computed: {
    dialogTitle() {
      return this.mode === 'edit' ? '编辑图书' : '新增图书'
    }
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    formatStatus(status) {
      return this.statusMap[status]
    },
    handleFilter() {
      this.listQuery.page = 1
      this.fetchData()
    },
    formatDate(date) {
      if (!date) return ''; // 如果日期为空，返回空字符串

      const d = new Date(date);
      if (isNaN(d.getTime())) return ''; // 检查日期是否有效

      const padZero = (num) => String(num).padStart(2, '0'); // 补零函数

      const year = d.getFullYear();
      const month = padZero(d.getMonth() + 1); // 月份从 0 开始
      const day = padZero(d.getDate());
      const hours = padZero(d.getHours());
      const minutes = padZero(d.getMinutes());
      const seconds = padZero(d.getSeconds());

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    clearInput() {
      this.listQuery.title = ''; // 清空输入框内容
      this.fetchData()
    },
    handleEdit(row) {
      // 将 publish_date 转换为 Date 对象
      const publishDate = new Date(row.registerDate);
      this.editForm = { ...row, publish_date: publishDate };
      this.mode = 'edit';
      this.dialogVisible = true;
    },
    handleDelete(row) {
      this.$confirm(`确定要删除《${row.title}》吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBook(row.id).then(response => {
          this.dialogVisible = false;
          this.$message.success(`已删除：${row.title}`);
          this.fetchData(); // 刷新数据
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.message
          });
        });
      }).catch(() => {
        // 取消删除操作
      });
    },
    handleClose() {
      this.dialogVisible = false;
      this.editForm = {
        id: '',
        isbn: '',
        title: '',
        author: '',
        publisher: '',
        publish_date: '',
        description: ''
      };
      this.mode = 'add'; // 重置为新增模式
    },
    onSubmit() {
      if (this.mode === 'edit') {
        updateBook(this.editForm).then(response => {
        this.$notify.success({
          title: '成功',
            message: '更新成功！'
        });
        this.dialogVisible = false;
        this.fetchData(); // 刷新数据
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.message
        });
      });
      } else {
        saveBook(this.editForm).then(response => {
          this.$notify.success({
            title: '成功',
            message: '提交成功！'
          });
          this.dialogVisible = false;
          this.fetchData(); // 刷新数据
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.message
          });
        });
      }
    },
    handleAdd() {
      this.editForm = {
        id: '',
        isbn: '',
        title: '',
        author: '',
        publisher: '',
        publish_date: '',
        description: ''
      };
      this.mode = 'add';
      this.dialogVisible = true;
    }
  }
}
</script>

<style lang="scss">
.el-icon-circle-close {
  font-size: 16px; /* 调整图标大小 */
  margin-right: 5px; /* 调整图标与输入框右侧的距离 */
  margin-top: 13px;
}
</style>
