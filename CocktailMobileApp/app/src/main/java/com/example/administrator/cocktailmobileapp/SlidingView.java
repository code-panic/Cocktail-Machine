package com.example.administrator.cocktailmobileapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class SlidingView extends ViewGroup {
    private static final String TAG = "SlidingView";

    // 드래그 속도와 방향을 판단하는 클래스
    private VelocityTracker mVelocityTracker = null;

    // 화면 전환을 위한 드래그 속도의 최소값 pixel/s (100 정도으로 속도로 이동하면 화면전환으로 인식)
    private static final int SNAP_VELOCITY = 100;

    /* 화면에 대한 터치이벤트가 화면전환을 위한 터치인가? 현 화면의 위젯동작을 위한
        터치인가? 구분하는 값 (누른상태에서 10px 이동하면 화면 이동으로 인식) */
    private int mTouchSlop = 10;

    private Bitmap mWallpaper = null; // 배경화면을 위한 비트맵
    private Paint mPaint = null;

    /* 화면 자동 전황을 위한 핵심 클래스 ( 화면 드래그후 손을 뗏을때
        화면 전환이나 원래 화면으로 자동으로 스크롤 되는 동작을 구현하는 클래스) */
    private Scroller mScroller = null;
    private PointF mLastPoint = null; // 마지막 터치 지점을 저장하는 클래스
    private int mCurPage = 0; // 현재 화면 페이지

    private int mCurTouchState; // 현재 터치의 상태
    private static final int TOUCH_STATE_SCROLLING = 0; // 현재 스크롤 중이라는 상태
    private static final int TOUCH_STATE_NORMAL = 1; // 현재 스크롤 상태가 아님

    private Toast mToast;

    public SlidingView(Context context) {
        super(context);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mWallpaper = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher_background); // 배경화면 불러오기
        mPaint = new Paint();
        mScroller = new Scroller(getContext()); // 스크롤러 클래스 생성
        mLastPoint = new PointF();
    }

    // 차일드뷰의 크기를 지정하는 콜백 메서드
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        for (int i = 0; i < getChildCount(); i++) {
            // 각 차일드뷰의 크기는 동일하게 설정
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    // 차일드뷰의 위치를 지정하는 콜백 메서드
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        // 핵심 구현 부분으로써
        // 차일드뷰들을 겹치지 않게 옆으로 차례대로 나열해서 배치한다.
        // 옆으로 차례대로 배치를 해놔야 스크롤을 통해 옆으로 이동하는것이 가능해진다.
        for (int i = 0; i < getChildCount(); i++) {
            int child_left = getChildAt(i).getMeasuredWidth() * i;
            getChildAt(i).layout(child_left, t, child_left + getChildAt(i).getMeasuredWidth(),
                    getChildAt(i).getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mWallpaper, 0, 0, mPaint); // 바탕화면을 그리고
        for (int i = 0; i < getChildCount(); i++) {
            drawChild(canvas, getChildAt(i), 100); // 차일드 뷰들을 하나하나 그린다.
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "event Action : " + event.getAction());

        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();

        // 터치되는 모든 좌표들을 저장하여, 터치 드래그 속도를 판단하는 기초를 만듬
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 현재 화면이 자동 스크롤 중이라면 (ACTION_UP 의 mScroller 부분 참조)
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation(); // 자동스크롤 중지하고 터치 지점에 멈춰서잇을것
                }
                mLastPoint.set(event.getX(), event.getY()); // 터치지점 저장
                break;

            case MotionEvent.ACTION_MOVE:
                // 이전 터치지점과 현재 터치지점의 차이를 구해서 화면 스크롤 하는데 이용
                int x = (int) (event.getX() - mLastPoint.x);
                scrollBy(-x, 0); // 차이만큼 화면 스크롤
                invalidate(); // 다시 그리기
                mLastPoint.set(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
                // pixel/ms 단위로 드래그 속도를 구할것인가 지정 (1초로 지정)
                // onInterceptTouchEvent 메서드에서 터치지점을 저장해둔 것을 토대로 한다.
                mVelocityTracker.computeCurrentVelocity(1000);
                int v = (int) mVelocityTracker.getXVelocity(); // x 축 이동 속도를 구함

                int gap = getScrollX() - mCurPage * getWidth(); // 드래그 이동 거리 체크
                Log.d(TAG, "mVelocityTracker : " + v);
                int nextPage = mCurPage;

                // 드래그 속도가 SNAP_VELOCITY 보다 높거니 화면 반이상 드래그 했으면
                // 화면전환 할것이라고 nextPage 변수를 통해 저장.
                if ((v > SNAP_VELOCITY || gap < -getWidth() / 2) && mCurPage > 0) {
                    nextPage--;
                } else if ((v < -SNAP_VELOCITY || gap > getWidth() / 2)
                        && mCurPage < getChildCount() - 1) {
                    nextPage++;
                }

                int move = 0;
                if (mCurPage != nextPage) { // 화면 전환 스크롤 계산
                    // 현재 스크롤 지점에서 화면전환을 위해 이동해야하는 지점과의 거리 계산
                    move = getChildAt(0).getWidth() * nextPage - getScrollX();
                } else { // 원래 화면 복귀 스크롤 계산
                    // 화면 전환 하지 않을것이며 원래 페이지로 돌아가기 위한 이동해야하는 거리 계산
                    move = getWidth() * mCurPage - getScrollX();
                }

                // 핵심!! 현재 스크롤 지점과 이동하고자 하는 최종 목표 스크롤 지점을 설정하는 메서드
                // 현재 지점에서 목표 지점까지 스크롤로 이동하기 위한 중간값들을 자동으로 구해준다.
                // 마지막 인자는 목표 지점까지 스크롤 되는 시간을 지정하는 것. 밀리세컨드 단위이다.
                // 마지막 인자의 시간동안 중간 스크롤 값들을 얻어 화면에 계속 스크롤을 해준다.
                // 그러면 스크롤 애니메이션이 되는것처럼 보인다. (computeScroll() 참조)
                mScroller.startScroll(getScrollX(), 0, move, 0, Math.abs(move));

                invalidate();
                mCurPage = nextPage;

                // 터치가 끝났으니 저장해두었던 터치 정보들 삭제하고
                // 터치상태는 일반으로 변경
                mCurTouchState = TOUCH_STATE_NORMAL;
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }

        return true;
    }

    // 완전 핵심!! 인 콜백 메서드. 스크롤 될때마다 무조건 계속 실행됨.
    @Override
    public void computeScroll() {
        super.computeScroll();
        // onTouchEvent 에서 지정된 mScroller 의 목표 스크롤 지점으로 스크롤하는데, 필요한 중간 좌표 값들을
        // 얻기 위한 메서드로서, 중간 좌표값을 얻을수 있으면 true 를 리턴
        if (mScroller.computeScrollOffset()) {
            // 값을 얻을수 있다면. getCurrX,getCurrY 을 통해 전달되는데,
            // 이는 목표 지점으로 스크롤하기 위한 중간 좌표값들을 Scroller 클래스가 자동으로 계산한 값이다.
            // scrollTo() 를 통해 화면을 중간 지점으로 스크롤 하고,
            // 앞서 말했듯 스크롤이 되면 자동으로 computeScroll() 메서드가 호출되기 때문에
            // 목표 스크롤 지점에 도착할떄까지 computeScroll() 메서드가 호출되고 스크롤 되고 호출되고 반복.
            // 따라서 화면에 스크롤 애니메이션을 구현된것처럼 보이게 됨.
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    // ViewGroup 의 childview 들에게 터치이벤트를 줄것인지 아니면 본인에게 터치이벤트를 줄것인지
    // 판단하는 콜백 메서드 ( 터치 이빈트 발생시 가장먼저 실행 됨 )
    // 리턴값으로 true 를 주게 되면 viewgroup의 onTouchEvent 메서드가 실행되고
    // false 를 주면 ViewGroup 의 onTouchEvent은 실행되지 않고 childview 에게
    // 터치 이벤틀르 넘겨주게 된다. 따라서, 화전 전환 할것인가? 차일드뷰의 버튼이나 여타 위젯을 컨트롤
    // 하는 동작인가? 를 구분하는 로직이 여기서 필요하다.
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent : " + ev.getAction());
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Scroller가 현재 목표지점까지 스크롤 되었지는 판단하는 isFinished() 를 통해
                // 화면이 자동 스크롤 되는 도중에 터치를 한것인지 아닌지를 확인하여,
                // 자식에게 이벤트를 전달해 줄건지를 판단한다.
                mCurTouchState = mScroller.isFinished() ? TOUCH_STATE_NORMAL
                        : TOUCH_STATE_SCROLLING;
                mLastPoint.set(x, y); // 터치 지점 저장
                break;
            case MotionEvent.ACTION_MOVE:
                // 자식뷰의 이벤트인가 아니면 화면전환 동작 이벤트를 판단하는 기준의 기본이 되는
                // 드래그 이동 거리를 체크 계산한다.
                int move_x = Math.abs(x - (int) mLastPoint.x);
                // 만약 처음 터치지점에서 mTouchSlop 만큼 이동되면 화면전환을 위한 동작으로 판단
                if (move_x > mTouchSlop) {
                    mCurTouchState = TOUCH_STATE_SCROLLING; // 현재 상태 스크롤 상태로 전환
                    mLastPoint.set(x, y);
                }
                break;
        }

        // 현재 상태가 스크롤 중이라면 true를 리턴하여 viewgroup의 onTouchEvent 가 실행됨
        return mCurTouchState == TOUCH_STATE_SCROLLING;
    }
}
