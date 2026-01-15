/**
 * CSR(Client-Side Rendering) 게시글 로드 및 렌더링 스크립트
 */
document.addEventListener('DOMContentLoaded', () => {
  // 초기 로딩 시 게시글 데이터를 가져옴
  fetchArticle();
});

/**
 * 서버로부터 최신 게시글 데이터를 비동기로 호출
 */
async function fetchArticle() {
  try {
    const response = await fetch('/api/article');

    // HTTP 응답 상태 확인
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const result = await response.json();

    if (result.status === 'success') {
      renderArticle(result.data);
    } else if (result.status === 'no_newest_found') {
      displayEmptyState("작성된 게시글이 없습니다.");
    }
  } catch (error) {
    console.error('데이터 통신 중 오류 발생:', error);
    displayEmptyState("데이터를 불러오는 중 오류가 발생했습니다.");
  }
}

/**
 * 수신된 Article 데이터를 기반으로 DOM 업데이트
 */
function renderArticle(article) {
  // 1. 작성자 정보 업데이트 (클래스 선택자 활용)
  const authorNickname = document.querySelector('.post__account__nickname');
  const authorImg = document.querySelector('.post__account__img');

  if (authorNickname) authorNickname.innerText = article.authorUserId;
  if (authorImg) authorImg.src = "./img/basic_profileImage.svg"; // 기본 이미지 혹은 데이터에서 주입

  // 2. 메인 이미지 업데이트
  const mainImage = document.getElementById('main-image');
  if (mainImage && article.images && article.images.length > 0) {
    mainImage.src = article.images[0].url; // Image 레코드의 url 필드 사용
  }

  // 3. 좋아요 수, 댓글 수 및 본문 업데이트
  const likeCount = document.getElementById('like-count');
  const articleContent = document.getElementById('article-content');

  const commentCount = document.getElementById('comment-count');
  if (commentCount) {
    commentCount.innerText = article.comments ? article.comments.length : 0;
  }

  if (likeCount) likeCount.innerText = article.likes || 0;
  if (articleContent) articleContent.innerText = article.content;

  // 4. 댓글 리스트 렌더링
  renderComments(article.comments);
}

/**
 * 댓글 리스트 동적 생성 및 "더보기" 로직 제어
 */
function renderComments(comments) {
  const commentList = document.getElementById('comment-list');
  const showAllBtn = document.getElementById('show-all-btn');

  if (!commentList) return;
  commentList.innerHTML = ''; // 기존 정적 더미 데이터 제거

  if (!comments || comments.length === 0) {
    showAllBtn.style.display = 'none';
    return;
  }

  comments.forEach((comment, index) => {
    const li = document.createElement('li');
    li.className = 'comment__item';

    // 초기 3개만 보여주고 나머지는 CSS의 hidden 클래스 등으로 제어 (필요 시)
    if (index >= 3) {
      li.classList.add('hidden');
      li.style.display = 'none'; // hidden 클래스에 style이 정의되어 있지 않을 경우 대비
    }

    li.innerHTML = `
            <div class="comment__item__user">
              <img class="comment__item__user__img" src="./img/basic_profileImage.svg" />
              <p class="comment__item__user__nickname">${comment.authorUserId}</p>
            </div>
            <p class="comment__item__article">${comment.content}</p>
        `;
    commentList.appendChild(li);
  });

  // 댓글이 3개 초과인 경우 버튼 활성화
  if (comments.length > 3) {
    showAllBtn.style.display = 'block';
    showAllBtn.innerText = `모든 댓글 보기(${comments.length}개)`;

    showAllBtn.onclick = () => {
      const hiddenComments = commentList.querySelectorAll('.comment__item.hidden');
      hiddenComments.forEach(item => {
        item.style.display = 'flex'; // 혹은 기존 CSS 구조에 맞게 제거
        item.classList.remove('hidden');
      });
      showAllBtn.style.display = 'none';
    };
  } else {
    showAllBtn.style.display = 'none';
  }
}

/**
 * 에러 발생 시 사용자 피드백 제공
 */
function displayEmptyState(message) {
  const wrapper = document.querySelector('.wrapper');
  if (wrapper) {
    // wrapper 내부의 모든 요소(.post, .comment, .nav 등)를 제거하고 메시지만 삽입
    wrapper.innerHTML = `
      <div style="display: flex; justify-content: center; align-items: center; min-height: 400px;">
        <p style="font-size: 18px; color: #4B5966; font-weight: 500;">
          ${message}
        </p>
      </div>
      `;
  }
}