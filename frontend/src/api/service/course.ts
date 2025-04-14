/**
 * 课程相关API
 */
import request from '../../utils/http/request';
import type { CourseInfo, CoursePublishInfo, CourseWithTeacherInfo, PaginationParams, PaginationResult } from '../types';

/**
 * 课程API服务
 */
const courseService = {
  /**
   * 获取课程列表
   * @param params 分页参数
   * @returns Promise
   */
  getCourseList(params: PaginationParams, departmentIds?: string[]) {
    return request.get<PaginationResult<CourseInfo>>('/course/getPage', { ...params, departmentIds });
  },

  getCourseWithTeachers(params: PaginationParams, courseId: number) {
    return request.get<PaginationResult<CourseWithTeacherInfo>>(`/course/${courseId}`, { ...params });
  },

  /**
   * 发布课程
   * @param course 课程发布信息
   * @returns Promise<number> 返回课程ID
   */
  publishCourse(course: CoursePublishInfo) {
    return request.post<number>('/course/createOrUpdate', course);
  },

  /**
   * 删除课程
   * @param courseId 课程ID
   * @returns Promise
   */
  deleteCourse(courseId: string) {
    return request.delete<void>(`/courses/${courseId}`);
  },

  /**
   * 学生选课
   * @param courseId 课程ID
   * @returns Promise
   */
  enrollCourse(courseId: string) {
    return request.post<void>(`/student/courses/${courseId}/enroll`);
  },

  /**
   * 学生退课
   * @param courseId 课程ID
   * @returns Promise
   */
  dropCourse(courseId: string) {
    return request.post<void>(`/student/courses/${courseId}/drop`);
  },

  /**
   * 获取学生已选课程
   * @returns Promise
   */
  getStudentCourses() {
    return request.get<CourseInfo[]>('/student/courses');
  },

  /**
   * 获取教师教授的课程
   * @returns Promise
   */
  getTeacherCourses(params: PaginationParams) {
    return request.get<PaginationResult<CourseWithTeacherInfo>>('/teacher/course/getPage', { ...params });
  },


  getTeacherIdsByCourseId(courseId: number) {
    return request.get<string[]>(`/course/${courseId}/teacherIds`);
  }
};

export default courseService;