<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="login-page">
	<div class="login-box">
	
		<div class="card card-outline card-primary">
			<div class="card-header text-center">
				<a href="../../index2.html" class="h1"><b>문당훈</b>페이지</a>
			</div>
			<div class="card-body">
				<p class="login-box-msg">Sign in to start your session</p>
				<form action="/login" method="post">
					<div class="input-group mb-3">
						<input type="text" name="username" class="form-control" placeholder="아이디를 입력해주세요" />
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-envelope"></span>
							</div>
						</div>
					</div>
					<div class="input-group mb-3">
						<input type="password" name="password" class="form-control" placeholder="비밀번호를 입력해주세요" />
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-lock"></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-8">
							<div class="icheck-primary">
								<input type="checkbox" name="remember-me" id="remember"> <label
									for="remember"> Remember Me </label>
							</div>
						</div>
	
						<div class="col-4">
							<button type="submit" class="btn btn-primary btn-block">Sign
								In</button>
						</div>
					</div>
					<sec:csrfInput/>
				</form>
			</div>
	
		</div>
	
	</div>
</div>